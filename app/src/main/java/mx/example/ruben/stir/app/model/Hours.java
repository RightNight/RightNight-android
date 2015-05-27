package mx.example.ruben.stir.app.model;

/**
 * Created by Ruben on 5/21/15.
 */
public class Hours
{
    public Hours(boolean isOpen, String status)
    {
        this.isOpen = isOpen;
        this.status = status;
    }

    private boolean isOpen;
    private String status;

    public boolean isOpen()
    {
        return isOpen;
    }

    public String getStatus() {
        return status;
    }
}
