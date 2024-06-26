package com.picross.collab.network.tcp;

import java.io.IOException;

import static com.picross.collab.network.common.Log.COMM;


public class TcpServerAsync extends TcpServer {

	public TcpServerAsync(final int port) throws IOException {
		super(port);
		getListenChannel().configureBlocking(false);
	}

	

	public TcpSocketAsync acceptClientAsync() throws IOException {

		COMM.trace("TcpSserveur::accept.");	

		return new TcpSocketAsync(getListenChannel().accept());

	}
	

}
