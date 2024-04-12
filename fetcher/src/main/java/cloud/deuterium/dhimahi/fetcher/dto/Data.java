package cloud.deuterium.dhimahi.fetcher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Milan Stojkovic 13-May-2023
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class Data {

    @XmlElement
    List<MetData> metData;
}
