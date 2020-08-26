package org.ft.server.exceptions;

/**
 * @author Prajwal Das
 */
public class FeatureToggleException extends RuntimeException
{
    public FeatureToggleException (String message)
    {
        super(message);
    }

    public static final FeatureToggleException APP_NOT_REGISTERED = new FeatureToggleException("App not registered");
}
