package movieObject;

public class witObj {
    public String getWitText() {
        return witText;
    }

    public void setWitText(String jsonText) {
        this.witText="";
        int textIndex= jsonText.indexOf("text")+8;
        char endPt;
        while(jsonText.charAt(textIndex)!='\"'){
            this.witText=this.witText+jsonText.charAt(textIndex);
            textIndex++;
        }
    }

    public String getWitIntent() {
        return witIntent;
    }

    public void setWitIntent(String jsonText) {
        this.witIntent="";
        if (jsonText.contains("intent")) {

            int intentIndex = jsonText.indexOf("intent");
            int intentValueIndex= jsonText.indexOf("value",intentIndex)+9;


            while(jsonText.charAt(intentValueIndex)!='\"') {
                this.witIntent=this.witIntent+jsonText.charAt(intentValueIndex);
                intentValueIndex++;
            }
        }
        else{
            witIntent="Intent not found";
        }
    }
    public String getWitMovieName() {
        return witMovieName;
    }

    public void setWitMovieName(String jsonText) {
        this.witMovieName="";
        if (jsonText.contains("movieName")) {
            int movieNameIndex = jsonText.indexOf("movieName");
            int middleIndex= jsonText.indexOf("value",movieNameIndex)+1;
            int movieNameValueIndex= (jsonText.indexOf("value",middleIndex))+9;
            while(jsonText.charAt(movieNameValueIndex)!='\"') {
                this.witMovieName=this.witMovieName+jsonText.charAt(movieNameValueIndex);
                movieNameValueIndex++;
            }
            witMovieName=witMovieName.replaceAll(" ","+");
        }
        else{
            witMovieName=null;
        }
    }

    public String getWitGenre() {
        return witGenre;
    }

    public void setWitGenre(String jsonText) {
        this.witGenre="";
        if (jsonText.contains("genre")) {
            int genreIndex = jsonText.indexOf("genre");
            int middleIndex= jsonText.indexOf("value",genreIndex)+1;
            int genreValueIndex= jsonText.indexOf("value",middleIndex)+9;

            while(jsonText.charAt(genreValueIndex)!='\"') {
                    this.witGenre = this.witGenre + jsonText.charAt(genreValueIndex);
                    genreValueIndex++;
            }

        }
        else{
            witGenre="Genres not found";
        }
    }

    String witText,witIntent,witMovieName,witGenre;




}
