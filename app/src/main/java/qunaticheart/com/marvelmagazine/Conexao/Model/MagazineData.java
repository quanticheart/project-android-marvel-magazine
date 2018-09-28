package qunaticheart.com.marvelmagazine.Conexao.Model;

import java.io.Serializable;
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

    public class thumbnail implements Serializable {

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

    public class prices implements Serializable {

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

    public class urls implements Serializable {

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

    public class dates implements Serializable {

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
}
