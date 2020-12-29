package be.vdab.allesvoordekeuken.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Access(AccessType.FIELD)
public class Korting {
    private int vanafaantal;
    private BigDecimal percentage;

    public Korting(int vanafaantal, BigDecimal percentage) {
        this.vanafaantal = vanafaantal;
        this.percentage = percentage;
    }

    protected Korting() {

    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Korting) {
            var andereKorting = (Korting) object;
            return vanafaantal == andereKorting.vanafaantal;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return vanafaantal;
    }

    public int getVanafaantal() {
        return vanafaantal;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }
}
