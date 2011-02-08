/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

function submitForm(buttonId, formId, action, method) {
	submitFormWithParams(buttonId, formId, action, method, null);
}

function submitFormWithParams(buttonId, formId, action, method, params) {
	if (buttonId != null) {
		var button = document.getElementById(buttonId);
		if (button != null) {
			button.disabled = true;
		}
	}
	var form = document.getElementById(formId);
	if (action != null) {
		form.setAttribute("action", action);
	}
	if (method != null) {
		form.setAttribute("method", method);
	}

	addHiddenFields(params, form);

	form.submit();
}

function pressButton(buttonId, url, method) {
	var button = document.getElementById(buttonId);
	if (button != null) {
		button.disabled = true;
	}
	sendRequest(url, null, method);
}

function pressButtonWithParams(buttonId, url, method, params) {
	var button = document.getElementById(buttonId);
	if (button != null) {
		button.disabled = true;
	}
	sendRequest(url, params, method);
}

function createField(name, value, form) {
	var hiddenField = document.createElement("input");
	hiddenField.setAttribute("type", "hidden");
	hiddenField.setAttribute("name", name);
	hiddenField.setAttribute("value", value);

	form.appendChild(hiddenField);
}
function addHiddenFields(params, form) {
	if (params != null) {
		for (var key in params) {
			createField(key, params[key], form);
		}
	}
}
function sendRequest(url, params, method) {

	var form = document.createElement("form");
	form.setAttribute("method", method);
	form.setAttribute("action", url);

	addHiddenFields(params, form);
	//addHiddenFields(getQueryVariables(url), form);


	document.body.appendChild(form);
	form.submit();
}

function disableInput(curCheckboxId, disabledInputId) {
	var disabledInput = document.getElementById(disabledInputId);
	var curCheckbox = document.getElementById(curCheckboxId);
	disabledInput.disabled = curCheckbox.checked;
}

function toggleInputById(inputId, disabled) {
	document.getElementById(inputId).disabled = disabled;
}

function enableInputById(inputId) {
	toggleInputById(inputId, false);
}

function disableInputById(inputId) {
	toggleInputById(inputId, true);
}

