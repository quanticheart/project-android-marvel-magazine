package qunaticheart.com.marvelmagazine.Conexao.Constants;

public class ConstantsConnect {
    public static String getBaseUrl() {
        return "https://gateway.marvel.com/v1/public/";
    }

    /**
     * @param url       url for connection
     * @param extencion extencion image
     * @return image url
     */
    public static String getFullImageCover(String url, String extencion) {
        return url + "." + extencion;
    }

    /**
     * @param url       url for connection
     * @param extencion extencion image
     * @return image url
     */
    public static String getFantasticImageCover(String url, String extencion) {
        return url + "/" + getPortraitFantastic() + "." + extencion;
    }

    /**
     * @param url       url for connection
     * @param extencion extencion image
     * @return image url Square
     */
    public static String getFantasticImageSquare(String url, String extencion) {
        return url + "/" + getSquareFantastic() + "." + extencion;
    }

    public static String getNumberFormated(Integer number) {
        return "#" + number;
    }

//========================================================================
//    Utils

    //cover
    private static String getPortraitFantastic() {
        return "portrait_fantastic";
    }

    //Square
    private static String getSquareFantastic() {
        return "standard_fantastic";
    }

//    portrait_small	50x75px
//    portrait_medium	100x150px
//    portrait_xlarge	150x225px
//    portrait_fantastic	168x252px
//    portrait_uncanny	300x450px
//    portrait_incredible	216x324px
//    Standard (square) aspect ratio

//    Square
//    standard_small	65x45px
//    standard_medium	100x100px
//    standard_large	140x140px
//    standard_xlarge	200x200px
//    standard_fantastic	250x250px
//    standard_amazing	180x180px

}
