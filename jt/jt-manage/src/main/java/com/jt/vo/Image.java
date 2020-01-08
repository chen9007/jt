package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Image {
		private  Integer error;
		private String  url;
		private  Integer width;
		private  Integer height;
		public static Image fail() {
			return new Image(1, null, null, null);
		}
}
