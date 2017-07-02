package io.sato.dbc.interceptor;


import io.sato.dbc.annotation.Contracted;
import io.sato.dbc.annotation.Ensures;
import io.sato.dbc.annotation.Invariant;
import io.sato.dbc.annotation.Requires;
import org.jboss.weld.interceptor.util.proxy.TargetInstanceProxy;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.lang.annotation.Annotation;

public class DBCInterceptor {

    public static final String SCRIPTING_ENGINE = "nashorn";
    private ScriptEngine scriptEngine = null;

    @AroundInvoke
    public Object executeContracts(InvocationContext ctx) throws Exception {

        Object targetInstance;
        Class targetClass;
        // check if the target is proxied
        if (ctx.getTarget() instanceof TargetInstanceProxy) {
            TargetInstanceProxy targetInstanceProxy = (TargetInstanceProxy) ctx.getTarget();
            targetInstance = targetInstanceProxy.getTargetInstance();
            targetClass = targetInstanceProxy.getTargetClass();
        } else {
            targetInstance = ctx.getTarget();
            targetClass = ctx.getTarget().getClass();
        }

        // check if the class is designed according to Design By Contract
        Contracted contracted = null;
        Annotation annotationContracted = targetClass.getAnnotation(Contracted.class);
        if (annotationContracted != null && annotationContracted instanceof Contracted) {
            contracted = (Contracted) annotationContracted;
        }

        // if the contract annotation available then execute invariant, requires and ensures expressions
        if (contracted != null) {
            // clean the engine
            Bindings bindings = getScriptEngine().getBindings(ScriptContext.ENGINE_SCOPE);
            bindings.clear();

            // set bean and params
            getScriptEngine().getContext().setAttribute("bean", targetInstance, ScriptContext.ENGINE_SCOPE);
            getScriptEngine().getContext().setAttribute("params", ctx.getParameters(), ScriptContext.ENGINE_SCOPE);
            // check if this method has any requirements
            Requires requires = ctx.getMethod().getDeclaredAnnotation(Requires.class);
            if (requires != null) {
                String[] expressions = requires.value();
                // execute expression
                for (String expression : expressions) {
                    Object evaluationResult = getScriptEngine().eval(expression);
                    if (evaluationResult == null || Boolean.FALSE.equals(evaluationResult)) {
                        throw new ContractException("Failed at @Requires Expression", expression);
                    }
                }
            }
        }

        // proceed to method
        Object result = ctx.proceed();

        // if the contract annotation available then execute invariant, requires and ensures expressions
        if (contracted != null) {
            // check if this method has any requirements
            Ensures ensures = ctx.getMethod().getDeclaredAnnotation(Ensures.class);
            if (ensures != null) {
                String[] ensureExpressions = ensures.value();
                // set result
                getScriptEngine().getContext().setAttribute("result", result, ScriptContext.ENGINE_SCOPE);
                // check if this method has any ensures
                for (String expression : ensureExpressions) {
                    Object evaluationResult = getScriptEngine().eval(expression);
                    if (evaluationResult == null || Boolean.FALSE.equals(evaluationResult)) {
                        throw new ContractException("Failed at @Ensures Expression", expression);
                    }
                }
            }

            // if there is an invariant it should be true
            Invariant invariant = null;
            Annotation annotationInvariant = targetClass.getAnnotation(Invariant.class);
            if (annotationInvariant != null && annotationInvariant instanceof Invariant) {
                invariant = (Invariant) annotationInvariant;
            }
            if (invariant != null) {
                String[] expressions = invariant.value();
                // check if this method has any invariants
                for (String expression : expressions) {
                    Object evaluationResult = getScriptEngine().eval(expression);
                    if (evaluationResult == null || Boolean.FALSE.equals(evaluationResult)) {
                        throw new ContractException("Failed at @Invariant Expression", expression);
                    }
                }
            }
        }

        // return method result
        return result;
    }

    private ScriptEngine getScriptEngine() {
        // scripting engine for expression evaluation
        if (scriptEngine == null) {
            scriptEngine = new ScriptEngineManager().getEngineByName(SCRIPTING_ENGINE);
        }
        return scriptEngine;
    }
}
