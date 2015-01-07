package Domini;

/**
 * Created by maikals on 07/01/15.
 */
public class Val {
    private String nomSubscripcio;
    private String data;

    public Val(String nomSubscripcio, String data) {
        this.nomSubscripcio = nomSubscripcio;
        this.data = data;
    }

    public String getNomSubscripcio() {
        return nomSubscripcio;
    }

    public void setNomSubscripcio(String nomSubscripcio) {
        this.nomSubscripcio = nomSubscripcio;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
