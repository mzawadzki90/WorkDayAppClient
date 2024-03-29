package michal.zawadzki.workdayappclient.api;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DictionariesDto {

    private MetaDto meta;

    private List<DictionaryDto> dictionaries;

}
