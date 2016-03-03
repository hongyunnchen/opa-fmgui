/**
 * Copyright (c) 2015, Intel Corporation
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *     * Redistributions of source code must retain the above copyright notice,
 *       this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Intel Corporation nor the names of its contributors
 *       may be used to endorse or promote products derived from this software
 *       without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*******************************************************************************
 *                       I N T E L   C O R P O R A T I O N
 *	
 *  Functional Group: Fabric Viewer Application
 *
 *  File Name: PreferencesInputValidator.java
 *
 *  Archive Source: $Source$
 *
 *  Archive Log:    $Log$
 *  Archive Log:    Revision 1.9  2015/12/09 16:08:42  jijunwan
 *  Archive Log:    PR 131944 - If "# Worst Nodes" is <10 or >100, there is a Entry Validation warning for the Refresh Rate
 *  Archive Log:
 *  Archive Log:    - improved PreferencesValidatorError to use local data rather than shared static data
 *  Archive Log:
 *  Archive Log:    Revision 1.8  2015/08/17 18:54:52  jijunwan
 *  Archive Log:    PR 129983 - Need to change file header's copyright text to BSD license txt
 *  Archive Log:    - changed frontend files' headers
 *  Archive Log:
 *  Archive Log:    Revision 1.7  2015/08/12 20:41:56  fisherma
 *  Archive Log:    PR 129747 - Time Window failed when updating recommended value in Time Window.  Updated the validation and upper limit on the time property.
 *  Archive Log:
 *  Archive Log:    Revision 1.6  2015/05/12 17:43:58  rjtierne
 *  Archive Log:    PR 128624 - Klocwork and FindBugs fixes for UI
 *  Archive Log:    - Initialize static instance to null
 *  Archive Log:    - Make the constructor private to enforce the Singleton pattern
 *  Archive Log:    - Make getInstance() synchronized to prevent duplicate object creation
 *  Archive Log:
 *  Archive Log:    Revision 1.5  2015/02/23 15:06:33  rjtierne
 *  Archive Log:    Simplified processing of refresh rate units
 *  Archive Log:
 *  Archive Log:    Revision 1.4  2015/02/20 21:14:47  rjtierne
 *  Archive Log:    Multinet Wizard: Using preferences model instead of PreferencesBean. Changed the way the refresh rate units is validated.
 *  Archive Log:
 *  Archive Log:    Revision 1.3  2015/02/10 23:18:06  jijunwan
 *  Archive Log:    changed to store refreshRate rather than refreshRateinSeconds, store TimeUnit by name rather than ordinary
 *  Archive Log:
 *  Archive Log:    Revision 1.2  2015/01/21 21:21:19  rjtierne
 *  Archive Log:    Supplying preferences wizard with sweep interval through Context
 *  Archive Log:    for comparison with refresh rate supplied by user input. Also providing
 *  Archive Log:    task scheduler to preferences wizard so user supplied refresh rate can
 *  Archive Log:    be updated.
 *  Archive Log:
 *  Archive Log:    Revision 1.1  2015/01/20 19:12:32  rjtierne
 *  Archive Log:    Initial Version
 *  Archive Log:
 *
 *  Overview: Custom input validator to check the validity of the inputs on the
 *  User Preferences Wizard view
 *
 *  @author: rjtierne
 *
 ******************************************************************************/

package com.intel.stl.ui.wizards.impl.preferences;

import java.util.concurrent.TimeUnit;

import com.intel.stl.ui.common.STLConstants;
import com.intel.stl.ui.common.Validator;
import com.intel.stl.ui.wizards.model.preferences.PreferencesModel;

public class PreferencesInputValidator {

    private final int OK = PreferencesValidatorError.OK.getId();

    private final int MAX_REFRESH_RATE = 1800;

    private final int MIN_TIMING_WINDOW = 1;

    // Setting Maximum timing window to 9999 seconds
    private final int MAX_TIMING_WINDOW = 9999;

    private final int MIN_NUM_WORST_NODES = 10;

    private final int MAX_NUM_WORST_NODES = 100;

    private static PreferencesInputValidator instance = null;

    private PreferencesInputValidator() {

    }

    public synchronized static PreferencesInputValidator getInstance() {
        if (instance == null) {
            instance = new PreferencesInputValidator();
        }

        return instance;
    }

    public int validate(PreferencesModel preferencesModel, int sweepInterval) {

        int errorCode = OK;

        errorCode = validateRefreshRateUnits(preferencesModel);
        if (errorCode == OK) {
            errorCode = validateRefreshRate(preferencesModel, sweepInterval);
            if (errorCode == OK) {
                errorCode = validateTimingWindow(preferencesModel);
                if (errorCode == OK) {
                    errorCode = validateNumWorstNodes(preferencesModel);
                }
            }
        }

        return errorCode;
    }

    protected int validateRefreshRate(PreferencesModel preferencesModel,
            int sweepInterval) {

        int errorCode = OK;

        try {
            // Assume refresh rate in seconds
            int refreshRate =
                    Integer.valueOf(preferencesModel.getRefreshRateInSeconds());
            String str = preferencesModel.getRefreshRateUnits().toUpperCase();
            TimeUnit unit = TimeUnit.valueOf(str);

            int refreshRateInSeconds =
                    (int) TimeUnit.SECONDS.convert(refreshRate, unit);

            if (!Validator.integerInRange(refreshRateInSeconds, sweepInterval,
                    MAX_REFRESH_RATE)) {
                PreferencesValidatorError error =
                        PreferencesValidatorError.REFRESH_RATE_OUT_OF_RANGE;
                error.setData(new Object[] { sweepInterval, MAX_REFRESH_RATE });
                errorCode = error.getId();
                // TODO get the real sweep interval and update the error message
            } else if (refreshRateInSeconds < sweepInterval) {
                PreferencesValidatorError error =
                        PreferencesValidatorError.REFRESH_RATE_THRESHOLD_ERROR;
                error.setData(new Object[] { sweepInterval });
                errorCode = error.getId();
            }
        } catch (NumberFormatException e) {

            errorCode =
                    PreferencesValidatorError.REFRESH_RATE_FORMAT_EXCEPTION
                            .getId();
        }

        if (Validator.isBlankOrNull(preferencesModel.getRefreshRateInSeconds())) {
            errorCode = PreferencesValidatorError.REFRESH_RATE_MISSING.getId();
        } else {

        }

        return errorCode;
    }

    protected int validateRefreshRateUnits(PreferencesModel preferencesModel) {

        int errorCode = OK;

        try {

            if (Validator.isBlankOrNull(preferencesModel.getRefreshRateUnits())) {
                errorCode =
                        PreferencesValidatorError.REFRESH_RATE_UNITS_MISSING
                                .getId();
            } else {

                // Convert the string to a number
                String str =
                        preferencesModel.getRefreshRateUnits().toUpperCase();
                int refreshRateUnits = TimeUnit.valueOf(str).ordinal();

                if ((refreshRateUnits != TimeUnit.SECONDS.ordinal())
                        && (refreshRateUnits != TimeUnit.MINUTES.ordinal())) {

                    errorCode =
                            PreferencesValidatorError.REFRESH_RATE_UNITS_OUT_OF_RANGE
                                    .getId();
                }
            }
        } catch (NumberFormatException e) {

            errorCode =
                    PreferencesValidatorError.REFRESH_RATE_UNITS_FORMAT_EXCEPTION
                            .getId();
        }

        return errorCode;
    }

    protected int validateTimingWindow(PreferencesModel preferencesModel) {

        int errorCode = OK;

        try {

            if (Validator.isBlankOrNull(preferencesModel
                    .getTimingWindowInSeconds())) {
                errorCode =
                        PreferencesValidatorError.TIMING_WINDOW_MISSING.getId();
            } else {
                int timingWindow =
                        Integer.parseInt(preferencesModel
                                .getTimingWindowInSeconds());

                if ((Integer.valueOf(timingWindow) instanceof Integer) == false) {
                    errorCode =
                            PreferencesValidatorError.TIMING_WINDOW_INVALID_TYPE
                                    .getId();
                } else if (!Validator.integerInRange(timingWindow,
                        MIN_TIMING_WINDOW, MAX_TIMING_WINDOW)) {
                    PreferencesValidatorError error =
                            PreferencesValidatorError.TIMING_WINDOW_OUT_OF_RANGE;
                    error.setData(new Object[] { MIN_TIMING_WINDOW,
                            MAX_TIMING_WINDOW });
                    errorCode = error.getId();

                }
            }
        } catch (NumberFormatException e) {

            errorCode =
                    PreferencesValidatorError.TIMING_WINDOW_FORMAT_EXCEPTION
                            .getId();
        }

        return errorCode;
    }

    protected int validateNumWorstNodes(PreferencesModel preferencesModel) {

        int errorCode = OK;

        try {

            if (Validator.isBlankOrNull(preferencesModel.getNumWorstNodes())) {
                errorCode =
                        PreferencesValidatorError.NUM_WORST_NODES_MISSING
                                .getId();
            } else {
                int numWorstNodes =
                        Integer.parseInt(preferencesModel.getNumWorstNodes());

                if ((Integer.valueOf(numWorstNodes) instanceof Integer) == false) {
                    errorCode =
                            PreferencesValidatorError.NUM_WORST_NODES_INVALID_TYPE
                                    .getId();
                } else if (!Validator.integerInRange(numWorstNodes,
                        MIN_NUM_WORST_NODES, MAX_NUM_WORST_NODES)) {
                    PreferencesValidatorError error =
                            PreferencesValidatorError.NUM_WORST_NODES_OUT_OF_RANGE;
                    error.setData(new Object[] { MIN_NUM_WORST_NODES,
                            MAX_NUM_WORST_NODES });
                    errorCode = error.getId();
                }
            }
        } catch (NumberFormatException e) {

            errorCode =
                    PreferencesValidatorError.NUM_WORST_NODES_FORMAT_EXCEPTION
                            .getId();
        }

        return errorCode;
    }

    public static String getTimeUnitString(TimeUnit unit) {
        if (unit == TimeUnit.SECONDS) {
            return STLConstants.K0012_SECONDS.getValue();
        } else if (unit == TimeUnit.MINUTES) {
            return STLConstants.K0011_MINUTES.getValue();
        } else {
            throw new RuntimeException("Unsupported time unit " + unit);
        }
    }

    public static TimeUnit getTimeUnit(String str) {
        if (str.equals(STLConstants.K0012_SECONDS.getValue())) {
            return TimeUnit.SECONDS;
        } else if (str.equals(STLConstants.K0011_MINUTES.getValue())) {
            return TimeUnit.MINUTES;
        } else {
            throw new RuntimeException("Unknown time unit string '" + str + "'");
        }
    }

    public static TimeUnit getTimeUnit(int value) {
        if (value == TimeUnit.SECONDS.ordinal()) {
            return TimeUnit.SECONDS;
        } else if (value == TimeUnit.MINUTES.ordinal()) {
            return TimeUnit.MINUTES;
        } else {
            throw new RuntimeException("Unknown time unit value '" + value
                    + "'");
        }
    }

    public static int getTimeUnitNumber(String str) {

        String seconds = STLConstants.K0012_SECONDS.getValue();
        String minutes = STLConstants.K0011_MINUTES.getValue();

        if (str.equals(seconds.toUpperCase())) {
            return TimeUnit.SECONDS.ordinal();
        } else if (str.equals(minutes.toUpperCase())) {
            return TimeUnit.MINUTES.ordinal();
        } else {
            throw new RuntimeException("Unknown time unit string '" + str + "'");
        }
    }

    public static String getTimeUnitString(int value) {
        if (value == TimeUnit.SECONDS.ordinal()) {
            return TimeUnit.SECONDS.name();
        } else if (value == TimeUnit.MINUTES.ordinal()) {
            return TimeUnit.MINUTES.name();
        } else {
            throw new RuntimeException("Unknown time unit value '" + value
                    + "'");
        }
    }

    public int getMaxTimingLimit() {
        return MAX_TIMING_WINDOW;
    }

    public int getMinNumWorstNode() {
        return MIN_NUM_WORST_NODES;
    }

    public int getMaxNumWorstNode() {
        return MAX_NUM_WORST_NODES;
    }
}
