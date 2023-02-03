package pe.edu.upc.banking.common.application;

public enum RequestBodyType {
    VALID {
        public String toString() {
            return "VALID";
        }
    },
    INVALID {
        public String toString() {
            return "INVALID";
        }
    },
}