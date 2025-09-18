import axios from "axios";

import {
  ALL_DOCTOR_REQUEST,
  ALL_DOCTOR_SUCCESS,
  ALL_DOCTOR_FAIL,
  GET_SECHUDLE_REQUEST,
  GET_SECHUDLE_SUCCESS,
  GET_SECHUDLE_FAIL,
  CLEAR_ERRORS,
} from "../constants/doctorConstant";

import { API_URL } from "../constants/apiConstant";

// Get all doctors
export const getDoctors =
  (keyword = "", currentPage = 0) =>
  async (dispatch) => {
    try {
      dispatch({ type: ALL_DOCTOR_REQUEST });

      const response = await axios.get(
        `${API_URL}/doctors?keyword=${keyword}&page=${currentPage}&size=6`
      );

      dispatch({
        type: ALL_DOCTOR_SUCCESS,
        payload: response.data,
      });
    } catch (error) {
      dispatch({
        type: ALL_DOCTOR_FAIL,
        payload: {
          message: error.response.data.message,
          status: error.response.status,
        },
      });
    }
  };
// Get scheduled doctors
export const getScheduledDoctors = (id) => async (dispatch) => {
  try {
    dispatch({ type: GET_SECHUDLE_REQUEST });

    const response = await axios.get(`${API_URL}/doctors/scheduled/${id}`);
    dispatch({
      type: GET_SECHUDLE_SUCCESS,
      payload: response.data,
    });
  } catch (error) {
    dispatch({
      type: GET_SECHUDLE_FAIL,
      payload: {
        message: error.response.data.message,
        status: error.response.status,
      },
    });
  }
};
//Clear Errors
export const clearErrors = () => async (dispatch) => {
  dispatch({
    type: CLEAR_ERRORS,
  });
};
