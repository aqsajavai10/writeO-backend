package org.writeo.utils.response;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class BackendResponse  {
    Object responsePayload;
    String responseDesc;
    String responseCode;

}
