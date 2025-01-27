package yurkov.cloudFileStorage.domain.storage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private int idOnPage;
    private String displayName;
    private String objectName;
    private String url;
    private String urlCurrentDirectory;
}
