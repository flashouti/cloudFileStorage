package yurkov.cloudFileStorage.domain.storage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BreadCrumb {
    private String name;
    private String url;
    private boolean current;
}