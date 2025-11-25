import {
  CHAT_REQUEST,
  CHAT_SUCCESS,
  CHAT_FAIL,
  CLEAR_ERRORS,
} from "../constants/chatConstant";

export const chatReducer = (state = { chatResponse: "" }, action) => {
  switch (action.type) {
    case CHAT_REQUEST:
      return {
        ...state,
      };
    case CHAT_SUCCESS:
      return {
        chatResponse: action.payload,
      };
    case CHAT_FAIL:
      return {
        ...state,
        error: action.payload,
      };
    case CLEAR_ERRORS:
      return {
        ...state,
        error: null,
      };
    default:
      return state;
  }
};
