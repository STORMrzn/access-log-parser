public class UserAgent {
    private final String typeOfOs;
    private final String browser;
    private boolean isBot;
    private int botCounter;

    public String getTypeOfOs() {
        return typeOfOs;
    }

    public String getBrowser() {
        return browser;
    }


    public UserAgent(String userAgentSep) {
        String typeOfOs=null;
        String browser=null;
        int botCounter=0;
        if (userAgentSep.contains("Win")) {typeOfOs="Windows";}
        if (userAgentSep.contains("Linux")) {typeOfOs="Linux";}
        if (userAgentSep.contains("Mac")) {typeOfOs="Mac";}

        if (userAgentSep.contains("Firefox/")) {browser="Firefox";}
        if (userAgentSep.contains("(KHTML, like Gecko)") && userAgentSep.contains("Safari/")) {browser="Chrome";}
        if (userAgentSep.contains("OPR/")) {browser="Opera";}
        if (userAgentSep.contains("Edg/")) {browser="Edge";} //в инструкции Edg/ = Edge
        if (userAgentSep.contains("Mobile/")) {browser="Safari";}

        this.typeOfOs = typeOfOs;
        this.browser = browser;
    }

    public boolean isBot(String userAgentSep) {
        if (userAgentSep.contains("Bot") || userAgentSep.contains("bot")) {
            isBot = true;
            System.out.println(isBot);
        }
        return false;
    }
}
