package io.sato.metrics;


import io.sato.dbc.annotation.Contracted;
import io.sato.dbc.annotation.Ensures;
import io.sato.dbc.annotation.Invariant;
import io.sato.dbc.annotation.Requires;
import io.sato.dbc.interceptor.DBCInterceptor;
import io.sato.types.Maybe;

import javax.interceptor.Interceptors;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Interceptors({DBCInterceptor.class})
@Contracted
@Invariant({
    "bean.getMetrics() != null"
})
public class MetricsAggregate {

    private final static Map<String, Object> metrics = new LinkedHashMap<>();

    @Ensures({
        "result!=null"
    })
    public Map<String, Object> getMetrics() {
        return Collections.unmodifiableMap(metrics);
    }

    @Requires({
        "params[0]!=null",
        "params[0]!=\"\"",
        "params[1]!=null"
    })
    public void add(String key, Object value) {
        metrics.put(key, value);
    }

    @Requires({
        "params[0]!=null",
        "params[0]!=\"\""
    })
    public boolean contains(String key) {
        return metrics.containsKey(key);
    }

    @Requires({
        "params[0]!=null",
        "params[0]!=\"\""
    })
    public Maybe<Object> get(String key) {
        return Maybe.create(metrics.get(key));
    }


}
