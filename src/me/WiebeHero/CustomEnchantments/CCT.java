package me.WiebeHero.CustomEnchantments;

public class CCT {
	public CCT() {
		
	}
	public String colorize(String msg)
    {
        String coloredMsg = "";
        for(int i = 0; i < msg.length(); i++)
        {
            if(msg.charAt(i) == '&') {
            	coloredMsg += '�';
            }     
            else
                coloredMsg += msg.charAt(i);
        }
        return coloredMsg;
    }
}