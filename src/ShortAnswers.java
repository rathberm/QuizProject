import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Diese Klasse kümmert sich um das Fragen stellen an Wolfram alpha
 *
 * @author Moritz Rathberger
 */
public class ShortAnswers {
    private final String APPID = "6GHRKV-R85EJLHKVY";

    /**
     * Baut aus dem Input String eine WolframAlpha Url
     *
     * @param pInput Der Input String(Die Frage)
     * @return Die fertige URl
     */
    private String buildUrl(String pInput) {

        //Standarteil der url
        String url = "http://api.wolframalpha.com/v1/result?appid=";
        url = url + APPID;
        url = url + "&i=";
        //splitted den input String
        String[] arr = pInput.split(" ");
        //fügt den gesplitteten Input String wieder zusammen
        //und setzt hinter jedes Wort ein +
        url = url + String.join("+", arr);
        url = url + "%3f&units=metric";
        //Replaced alle leerzeichen die eventuell noch vorhanden sind
        url.replaceAll("\\s+", "");

        return url;
    }

    /**
     * Ruft Wolfram auf, liest die Seite aus und gibt den Inhalt in der Konsole aus
     *
     * @param pInput Die Frage
     */
    public String queryWolfram(String pInput) {

        String urlString = buildUrl(pInput);

        try {
            URL url = new URL(urlString);
            //Erzeugt einen Reader der den InputStream der Seite liest
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

            String line;
            //Wenn die gelesene Zeile keine leere Zeile ist, wird sie in der Kosole ausgegeben
            while ((line = reader.readLine()) != null) {
                return line;
            }
            reader.close();
            return "-1";
        } catch (Exception e) {
            System.out.println("Diese eingabe wurde nicht richtig erkannt.");
            System.out.println("Bist du sicher das die Eingabe in Englisch war?");
            System.out.println("Bist du sicher das es eine Frage war?");
            return "-1";
        }
    }
}


