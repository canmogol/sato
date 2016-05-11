package io.sato.internal.service.file;

import com.fererlab.dispatch.service.AbstractService;

import java.util.HashSet;

/**
 * Expected functionality:
 * list files/folder at a path,
 * tree representation of a path
 * file/folder operations; read(file), update(file), create, delete, move(rename)
 */
public class FileSystemService extends AbstractService {
    public FileSystemService() {
        super(new HashSet<>());
    }
}
