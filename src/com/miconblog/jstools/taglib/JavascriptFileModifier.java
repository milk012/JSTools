/*
 * @(#)JavascriptFileModifier.java $version 2011. 12. 12.
 *
 * Copyright 2007 NHN Corp. All rights Reserved. 
 * NHN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.miconblog.jstools.taglib;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author tugs
 */
public class JavascriptFileModifier implements FileModifier {

	// white space character + ;;;
	private static Pattern testCodePattern = Pattern.compile("^[\\s]*(;;;).*");

	@Override
	public String modify(String fileContent) {
		return StringUtils.isBlank(fileContent) ? StringUtils.EMPTY : deleteTestCode(fileContent);
	}

	private String deleteTestCode(String fileContent) {

		StringBuilder result = new StringBuilder();

		try {

			@SuppressWarnings("unchecked")
			List<String> readLines = IOUtils.readLines(new ByteArrayInputStream(fileContent.getBytes("UTF-8")), "UTF-8");

			for (String line : readLines) {
				if (testCodePattern.matcher(line).matches() == false) {
					result.append(line).append("\n");
				}
			}

			return result.toString();

		} catch (IOException e) {
			return StringUtils.EMPTY;
		}
	}
}
