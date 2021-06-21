package by.panasenko.webproject.tag;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class CustomImageTag extends TagSupport {
    private static final Logger logger = LogManager.getLogger(CustomImageTag.class);

    @Override
    public int doStartTag() {
        try {
            JspWriter out = pageContext.getOut();
            out.write("<img class=\"img-responsive\" src=\"static/image/flower1.jpg\" style=\"margin-top: -75px;\">");
        } catch (IOException e) {
            logger.error("Error while writing to out stream for tag", e);
        }
        return SKIP_BODY;
    }
}
