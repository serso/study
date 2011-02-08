/*
 * Copyright (c) 2009-2010. Created by serso.
 *
 * For more information, please, contact serso1988@gmail.com.
 */

package org.solovyev.study.resources;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.solovyev.common.definitions.Message;
import org.solovyev.common.definitions.MessageImpl;
import org.solovyev.common.definitions.MessageType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: serso
 * Date: Mar 29, 2010
 * Time: 12:01:34 AM
 */
public class MessageCollector {

	private @NotNull List<Message> messages = new ArrayList<Message>();
	private @Nullable MessageType errorLevel = null;

	public MessageCollector() {
	}

	public @NotNull List<Message> getMessages() {
		return Collections.unmodifiableList(messages);
	}

	public void addMessage (String messageCode, MessageType messageType, Object... params) {
		addMessage(new MessageImpl(messageCode, messageType, params));
	}

	public void addMessage (String messageCode) {
		this.addMessage(messageCode, MessageType.info);
	}

	public void addMessage (Message message) {
		if (!this.messages.contains(message)) {
			setErrorLevel(message.getMessageType());
			this.messages.add(message);
		}
	}

	private void setErrorLevel(@NotNull MessageType errorLevel) {
		this.errorLevel = MessageType.getMessageTypeWithHigherLevel(this.errorLevel, errorLevel);
	}

	public @Nullable
	MessageType getErrorLevel() {
		return errorLevel;
	}

	public void clear() {
		this.errorLevel = null;
		this.messages.clear();
	}
}
