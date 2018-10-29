package com.it.netty.protocol.common;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;

public class MarShallingCodecFactory {

	
	public static void main(String[] args) {
		MarShallingCodecFactory.buildMarshallingDecoder();
		MarshallerFactory marshallerFactory = Marshalling.getMarshallerFactory("serial", Marshalling.class.getClassLoader());
	}
	public static MarshallingDecoder buildMarshallingDecoder() {
		
		MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		DefaultUnmarshallerProvider unmarshallerProvider = new DefaultUnmarshallerProvider(marshallerFactory,
				configuration);
		MarshallingDecoder marshallingDecoder = new MarshallingDecoder(unmarshallerProvider, 1024);
		return marshallingDecoder;
	}

	public static MarshallingEncoder buildMarshallingEncoder() {

		MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		MarshallingConfiguration marshallingConfiguration = new MarshallingConfiguration();
		marshallingConfiguration.setVersion(5);
		DefaultMarshallerProvider marshallerProvider = new DefaultMarshallerProvider(marshallerFactory,
				marshallingConfiguration);
		MarshallingEncoder marshallingEncoder = new MarshallingEncoder(marshallerProvider);
		return marshallingEncoder;
	}
}
