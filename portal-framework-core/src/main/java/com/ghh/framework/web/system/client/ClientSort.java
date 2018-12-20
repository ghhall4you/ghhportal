package com.ghh.framework.web.system.client;

import java.util.Comparator;

import com.ghh.framework.common.dto.ClientDTO;

public class ClientSort implements Comparator<ClientDTO> {

	public int compare(ClientDTO prev, ClientDTO now) {
		return (int) (now.getLogindatetime().getTime() - prev.getLogindatetime().getTime());
	}

}
