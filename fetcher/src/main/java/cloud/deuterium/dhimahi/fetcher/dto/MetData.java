package cloud.deuterium.dhimahi.fetcher.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by Milan Stojkovic 13-May-2023
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MetData {

    @XmlElement(name = "domain_lat")
    Double lat;

    @XmlElement(name = "domain_lon")
    Double lon;

    @XmlElement(name = "domain_title")
    String name;

    @XmlElement(name = "t")
    Double temperature;

    @XmlElement(name = "rh")
    Double humidity;
}
