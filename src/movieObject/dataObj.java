package movieObject;

public class dataObj {
    String dataTitle;
    String dataGenre;
    String dataPlot;
    String dataImdbRating;


    public String getdataPlot() {
        return dataPlot;
    }

    public void setdataPlot(String dataPlot) {
        this.dataPlot = dataPlot;
    }



    public String getdataImdbRating() {
        return dataImdbRating;
    }

    public void setdataImdbRating(String dataImdbRating) {
        this.dataImdbRating = dataImdbRating;
    }



    public String getdataTitle() {
        return dataTitle;
    }

    public void setdataTitle(String title) {
        dataTitle = title;
    }

    public String getdataGenre() {
        return dataGenre;
    }

    public void setdataGenre(String dataGenre) {
        this.dataGenre = dataGenre;
    }



    public void printdataTitle() {
        System.out.println(this.dataTitle);
    }


    public void printdataImdbRating() {
        System.out.println(this.dataImdbRating);
    }


    public void printGenre() {
        System.out.println(this.dataGenre);
    }

    public boolean searchGenre(String searchedGenre){
        return this.dataGenre.contains(searchedGenre);
    }

}
