package mx.example.ruben.stir.app.model;

/**
 * Created by Ruben on 5/21/15.
 */
public class Price
{
    private int tier;
    private String currency;

    public int getTier() {
        return tier;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString()
    {
        switch (tier)
        {
            case 1:
                return "Barato";
            case 2:
                return "Moderado";
            case 3:
                return "Caro";
            case 4:
                return  "Muy Caro";
            default:
                return "Sin Dato";
        }
    }
}
