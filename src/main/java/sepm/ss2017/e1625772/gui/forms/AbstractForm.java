package sepm.ss2017.e1625772.gui.forms;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public abstract class AbstractForm {
    protected List<String> parsingErrors = new ArrayList<>();
    protected List<String> validationErrors = new ArrayList<>();

    protected abstract void doParse();

    public boolean parse() {
        parsingErrors.clear();
        doParse();
        return parsingErrors.isEmpty();
    }

    protected abstract void check();

    /**
     * First call this method before checking for validation errors
     *
     * @return whether the form is valid
     */
    public boolean isValid() {
        validationErrors.clear();
        check();
        return validationErrors.isEmpty();
    }

    public boolean parseAndValidate() {
        return parse() && isValid();
    }

    public String errorMessages() {
        return parsingErrorMessages() + "\n" + validationErrorMessages() + "\n";
    }

    public String parsingErrorMessages() {
        StringBuilder sb = new StringBuilder("");
        for (String error : parsingErrors)
            sb.append(error).append("\n");
        return sb.toString();

    }

    public String validationErrorMessages() {
        StringBuilder sb = new StringBuilder("");
        for (String error : validationErrors)
            sb.append(error).append("\n");
        return sb.toString();
    }
}
