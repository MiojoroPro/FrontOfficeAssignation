package com.assignation.models;

import java.util.List;

public class ReservationApiResponse {
    private InnerData data;
    private String status;
    private int code;
    private int count;

    public ReservationApiResponse() {}

    public InnerData getData() {
        return data;
    }

    public void setData(InnerData data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class InnerData {
        private List<Reservation> data;
        private String status;
        private int code;
        private int count;

        public InnerData() {}

        public List<Reservation> getData() {
            return data;
        }

        public void setData(List<Reservation> data) {
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
