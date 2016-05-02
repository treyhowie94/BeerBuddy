/**
 *
 */
package com.beerbuddy.account;

/**
 * @author Christian Witchger
 *
 *         Verifies that the values passed follow the requirements. The default
 *         verification checks that required fields are not null
 */
public class VerifyValues {

	private String[] parameters;

	private boolean valid;

	/**
	 * Takes in parameters that need to be verified and runs the default
	 * required check.
	 *
	 * @param parameters
	 *            the parameters that need to be verified.
	 */
	public VerifyValues(String[] parameters) {
		this.parameters = parameters;
		checkNotEmpty();
	}

	/**
	 * Checks to see that the parameter array doesn't have any empty of null
	 * values.
	 *
	 * @return boolean if they the parameters are not null and not empty strings
	 */
	public boolean checkNotEmpty() {
		this.valid = false;

		for (String value : this.parameters) {
			if ((value != null) && !value.equals("")) {
				this.valid = true;
			} else {
				this.valid = false;
				break;
			}
		}

		return this.valid;
	}

	/**
	 * @return if the parameters are valid
	 */
	public boolean isValid() {
		return this.valid;
	}

}
