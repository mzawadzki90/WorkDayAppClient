/*
    Created on 20/06/2020 by uex1421
 */
package michal.zawadzki.workdayappclient.api.worker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import michal.zawadzki.workdayappclient.api.DictionaryDto;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OccupationDto {

    private int id;

    private DictionaryDto department;

    private DictionaryDto team;

    private DictionaryDto position;

    private Date since;

    private Date till;

}
