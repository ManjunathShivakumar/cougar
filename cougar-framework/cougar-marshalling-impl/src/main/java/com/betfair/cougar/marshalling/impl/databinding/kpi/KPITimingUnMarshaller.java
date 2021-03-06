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

package com.betfair.cougar.marshalling.impl.databinding.kpi;

import java.io.InputStream;

import com.betfair.cougar.core.api.transcription.ParameterType;
import com.betfair.cougar.marshalling.api.databinding.UnMarshaller;
import com.betfair.tornjak.kpi.KPIMonitor;

/**
 * A wrapper which stores KPI statistics for a {@link UnMarshaller}.
 */
public class KPITimingUnMarshaller implements UnMarshaller {

    private final KPIMonitor monitor;
    private final String  kpiName;
    private final UnMarshaller unmarshaller;
    
    public KPITimingUnMarshaller(KPIMonitor monitor, String kpiName, UnMarshaller marshaller) {
        this.monitor = monitor;
        this.kpiName = kpiName;
        this.unmarshaller = marshaller;
    }

	@Override
	public String getFormat() {
		return unmarshaller.getFormat();
	}

    @Override
    public Object unmarshall(InputStream inputStream, ParameterType parameterType, String encoding) {
        boolean success = false;
        long start = System.currentTimeMillis();
        try {
            Object result = unmarshaller.unmarshall(inputStream, parameterType,  encoding);
            success = true;
            return result;
        }
        finally {
            monitor.addEvent(kpiName, System.currentTimeMillis() - start, success);
        }
    }

    @Override
    public Object unmarshall(InputStream inputStream, Class<?> clazz, String encoding) {
        boolean success = false;
        long start = System.currentTimeMillis();        
        try {
            Object result = unmarshaller.unmarshall(inputStream, clazz, encoding);
            success = true;
            return result;
        }
        finally {
            monitor.addEvent(kpiName, System.currentTimeMillis() - start, success);
        }
    }
}
