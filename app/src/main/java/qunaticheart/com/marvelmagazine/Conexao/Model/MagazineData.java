package qunaticheart.com.marvelmagazine.Conexao.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MagazineData implements Serializable {

    private Integer id;
    private Integer digitalId;
    private String title;
    private Integer issueNumber;
    private String variantDescription;
    private String description;
    private String modified;
    private String isbn;
    private String like;
    private String upc;
    private String diamondCode;
    private String ean;
    private String issn;
    private String format;
    private Integer pageCount;
    private thumbnail thumbnail;
    private List<prices> prices;
    private List<dates> dates;
    private List<urls> urls;

    public MagazineData() {
        this.id = getIntError();
        this.digitalId = getIntError();
        this.title = getStringError();
        this.issueNumber = getIntError();
        this.variantDescription = getStringError();
        this.description = getStringError();
        this.modified = getStringError();
        this.isbn = getStringError();
        this.like = "0";
        this.upc = getStringError();
        this.diamondCode = getStringError();
        this.ean = getStringError();
        this.issn = getStringError();
        this.format = getStringError();
        this.pageCount = getIntError();
        this.thumbnail = null;
        this.prices = new ArrayList<>();
        this.dates = new ArrayList<>();
        this.urls = new ArrayList<>();
    }

    public MagazineData(Integer id, Integer digitalId, String title, Integer issueNumber, String variantDescription, String description, String modified, String isbn, String like, String upc, String diamondCode, String ean, String issn, String format, Integer pageCount, MagazineData.thumbnail thumbnail, List<MagazineData.prices> prices, List<MagazineData.dates> dates, List<MagazineData.urls> urls) {
        this.id = id;
        this.digitalId = digitalId;
        this.title = title;
        this.issueNumber = issueNumber;
        this.variantDescription = variantDescription;
        this.description = description;
        this.modified = modified;
        this.isbn = isbn;
        this.like = like;
        this.upc = upc;
        this.diamondCode = diamondCode;
        this.ean = ean;
        this.issn = issn;
        this.format = format;
        this.pageCount = pageCount;
        this.thumbnail = thumbnail;
        this.prices = prices;
        this.dates = dates;
        this.urls = urls;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(Integer digitalId) {
        this.digitalId = digitalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(Integer issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getVariantDescription() {
        return variantDescription;
    }

    public void setVariantDescription(String variantDescription) {
        this.variantDescription = variantDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getDiamondCode() {
        return diamondCode;
    }

    public void setDiamondCode(String diamondCode) {
        this.diamondCode = diamondCode;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<prices> getPrices() {
        return prices;
    }

    public void setPrices(List<prices> prices) {
        this.prices = prices;
    }

    public List<dates> getDates() {
        return dates;
    }

    public void setDates(List<dates> dates) {
        this.dates = dates;
    }

    public List<MagazineData.urls> getUrls() {
        return urls;
    }

    public void setUrls(List<MagazineData.urls> urls) {
        this.urls = urls;
    }


    //===========================================================================

    public static class thumbnail implements Serializable {

        public String path;

        public String extension;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }
    }

    public static class prices implements Serializable {

        public String type;

        public float price;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

    }

    public static class urls implements Serializable {

        public String type;

        public String url;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

    public static class dates implements Serializable {

        public String type;

        public String date;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    //utils
    //==============================================================================================

    private int getIntError() {
        return -99;
    }

    private String getStringError() {
        return "";
    }
}
