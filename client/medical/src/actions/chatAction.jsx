import {
  CHAT_REQUEST,
  CHAT_SUCCESS,
  CHAT_FAIL,
} from "../constants/chatConstant";
import axios from "axios";
import { API_URL } from "../constants/apiConstant";

export const getChatResponse = (message) => async (dispatch) => {
  try {
    dispatch({ type: CHAT_REQUEST });

    const response = await axios.post(`${API_URL}/chat`, { message });
    const chatResponse = response.data.data.response;
    dispatch({
      type: CHAT_SUCCESS,
      payload: response.data,
    });

    return chatResponse;
  } catch (error) {
    dispatch({
      type: CHAT_FAIL,
      payload: error.message,
    });
  }
};
