import ChatBot from "react-chatbotify";
import { useDispatch, useSelector } from "react-redux";
import { getChatResponse } from "../../actions/chatAction";
import { useEffect } from "react";
import { toast } from "react-toastify";

const Chat = () => {
  const dispatch = useDispatch();
  const { error } = useSelector((state) => state.chat);

  useEffect(() => {
    if (error) {
      toast.error("Đã có lỗi xảy ra vui lòng thử lại sau", {
        position: "top-right",
        onClose: () => dispatch({ type: "CLEAR_ERRORS" }),
      });
    }
  }, [error, dispatch]);

  const run = async (userInput) => {
    return dispatch(getChatResponse(userInput));
  };

  const flow = {
    start: {
      message: "Xin chào, tôi có thể giúp gì được cho bạn",
      path: "model_loop",
    },
    model_loop: {
      message: async (params) => {
        return await run(params.userInput);
      },
      path: "model_loop",
    },
  };

  return <ChatBot flow={flow} />;
};

export default Chat;
