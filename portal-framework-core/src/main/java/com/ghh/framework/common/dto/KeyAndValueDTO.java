package com.ghh.framework.common.dto;

/*****************************************************************
 *
 * 键与值的DTO或者是2个属性的DTO 类型由开发人员自己控制 {@code
 * 	 KeyAndValueDTO<int,String> dto = new KeyAndValueDTO<int,String>();
 * }
 *
 * @author ghh
 * @date 2018年12月19日下午10:02:14
 * @since v1.0.1
 ****************************************************************/
public class KeyAndValueDTO<keyName, valueName> {

	private keyName key;
	private valueName value;

	public KeyAndValueDTO() {
	}

	public KeyAndValueDTO(keyName key, valueName value) {
		this.key = key;
		this.value = value;
	}

	public keyName getKey() {
		return this.key;
	}

	public void setKey(keyName key) {
		this.key = key;
	}

	public valueName getValue() {
		return this.value;
	}

	public void setValue(valueName value) {
		this.value = value;
	}
}
