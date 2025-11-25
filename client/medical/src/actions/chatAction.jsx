import {
  CHAT_REQUEST,
  CHAT_SUCCESS,
  CHAT_FAIL,
} from "../constants/chatConstant";

export const getChatResponse = (message) => async (dispatch) => {
  try {
    dispatch({ type: CHAT_REQUEST });

    const response = await new Promise((resolve) =>
      setTimeout(() => resolve({ data: `Response to: ${message}` }), 1000)
    );
    dispatch({
      type: CHAT_SUCCESS,
      payload: response.data,
    });
    return response.data;
  } catch (error) {
    dispatch({
      type: CHAT_FAIL,
      payload: error.message,
    });
  }
};
