/*
 * Copyright 2013, The Sporting Exchange Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.betfair.cougar.core.api.exception;

import com.betfair.cougar.api.ResponseCode;
import com.betfair.cougar.api.security.CredentialFaultCode;

public enum ServerFaultCode {
	StartupError(ResponseCode.InternalError, 1),
	FrameworkError(ResponseCode.InternalError, 2),
	InvocationResultIncorrect(ResponseCode.InternalError, 3),
	ServiceCheckedException(null, 4), // Response code defined by the checked exception
	ServiceRuntimeException(ResponseCode.InternalError, 5),
	SOAPDeserialisationFailure(ResponseCode.BadRequest, 6),
	XMLDeserialisationFailure(ResponseCode.BadRequest, 7),
	JSONDeserialisationParseFailure(ResponseCode.BadRequest, 8),
	ClassConversionFailure(ResponseCode.BadRequest, 9),
	InvalidInputMediaType(ResponseCode.UnsupportedMediaType, 10),
	ContentTypeNotValid(ResponseCode.UnsupportedMediaType, 11),
	MediaTypeParseFailure(ResponseCode.UnsupportedMediaType, 12),
	AcceptTypeNotValid(ResponseCode.MediaTypeNotAcceptable, 13),
	ResponseContentTypeNotValid(ResponseCode.InternalError, 14),
	SecurityException(ResponseCode.Forbidden, 15),
	MandatoryNotDefined(ResponseCode.BadRequest, 18),
	Timeout(ResponseCode.Timeout,19),
	BinDeserialisationParseFailure(ResponseCode.BadRequest, 20),
	NoSuchOperation(ResponseCode.NotFound, 21),
    SubscriptionAlreadyActiveForEvent(ResponseCode.InternalError, 22),
    NoSuchService(ResponseCode.NotFound, 23),
    RescriptDeserialisationFailure(ResponseCode.BadRequest, 24),
    JMSTransportCommunicationFailure(ResponseCode.InternalError, 25),
	RemoteCougarCommunicationFailure(ResponseCode.ServiceUnavailable, 26),
    OutputChannelClosedCantWrite(ResponseCode.CantWriteToSocket, 27),
    XMLSerialisationFailure(ResponseCode.InternalError, 28),
    JSONSerialisationFailure(ResponseCode.InternalError, 29),
    SOAPSerialisationFailure(ResponseCode.InternalError, 30),
    NoRequestsFound(ResponseCode.BadRequest, 31),
    UnidentifiedCaller(ResponseCode.BadRequest, 33, CredentialFaultCode.UnidentifiedCaller),
    UnknownCaller(ResponseCode.BadRequest, 34, CredentialFaultCode.UnknownCaller),
    UnrecognisedCredentials(ResponseCode.BadRequest, 35, CredentialFaultCode.UnrecognisedCredentials),
    InvalidCredentials(ResponseCode.BadRequest, 36, CredentialFaultCode.InvalidCredentials),
    SubscriptionRequired(ResponseCode.Forbidden, 37, CredentialFaultCode.SubscriptionRequired),
    OperationForbidden(ResponseCode.Forbidden, 38, CredentialFaultCode.OperationForbidden),
    NoLocationSupplied(ResponseCode.BadRequest, 39, CredentialFaultCode.NoLocationSupplied),
    BannedLocation(ResponseCode.Forbidden, 40, CredentialFaultCode.BannedLocation);

    private final ResponseCode errorCode;
    private final String errorString;
    private final String toString;
    private final CredentialFaultCode cfc;
    public static final String COUGAR_EXCEPTION_PREFIX="DSC";


    private ServerFaultCode(ResponseCode errorCode, int detailCode) {
        this(errorCode, detailCode, null);
    }

	private ServerFaultCode(ResponseCode errorCode, int detailCode, CredentialFaultCode cfc) {
		this.errorCode = errorCode;
		errorString = COUGAR_EXCEPTION_PREFIX+"-"+String.format("%04d", detailCode);
        toString = name() + "(" + errorString + ")";
        this.cfc = cfc;
	}
	
	public String getDetail() {
		return errorString;
	}
	
	public ResponseCode getResponseCode() {
		return errorCode;
	}

    public CredentialFaultCode getCredentialFaultCode() {
        return cfc;
    }

    public static ServerFaultCode getByDetailCode(String prefix) {
        for (ServerFaultCode sfc : ServerFaultCode.values()) {
            if (sfc.getDetail().equals(prefix)) {
                return sfc;
            }
        }
        return null;
    }

    public static ServerFaultCode getByCredentialFaultCode(CredentialFaultCode credentialFaultCode) {
        for (ServerFaultCode sfc : ServerFaultCode.values()) {
            CredentialFaultCode cfc = sfc.getCredentialFaultCode();
            if (cfc != null && cfc.equals(credentialFaultCode)) {
                return sfc;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return toString;
    }
}
