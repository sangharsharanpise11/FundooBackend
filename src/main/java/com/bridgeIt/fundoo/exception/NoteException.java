package com.bridgeIt.fundoo.exception;

public class NoteException extends RuntimeException
{
		private static final long serialVersionUID = 1L;
		private int errorCode;
		public NoteException(String message , int errorCode) {
			super(message);
			this.errorCode = errorCode;
		}
		public int getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}
		
	}

