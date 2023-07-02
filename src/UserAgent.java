public class UserAgent {
    private final String typeOfOs;
    private final String browser;

    public String getTypeOfOs() {
        return typeOfOs;
    }

    public String getBrowser() {
        return browser;
    }

    public UserAgent(String userAgentSep) {
        this.typeOfOs = typeOfOs;
        this.browser = browser;
    }

    /*private UserAgent (String userAgentSep){
        //ff Mozilla/5.0 (platform; rv:geckoversion) Gecko/geckotrail Firefox/firefoxversion
        //chrome Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36
        //opera Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36 OPR/38.0.2220.41
        //edge Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36 Edg/91.0.864.59
        //safari Mozilla/5.0 (iPhone; CPU iPhone OS 13_5_1 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.1 Mobile/15E148 Safari/604.1

    }*/

}
