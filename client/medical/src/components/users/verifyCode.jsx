import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  Grid,
  Paper,
  Avatar,
  TextField,
  Button,
  Typography,
} from "@mui/material";
import { AccountCircle } from "@mui/icons-material";

import MetaData from "../layouts/metadata";
import Loader from "../layouts/loader";

import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { useDispatch, useSelector } from "react-redux";

import { verifyCode, clearErrors } from "../../actions/userAction";
const VerifyCode = ({ userId, email, password, confirmPassword }) => {
  document.body.style.backgroundColor = "#ccc";
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const [code, setCode] = useState("");
  const { message, error, loading } = useSelector((state) => state.verifyCode);
  const handleCodeChange = (event) => {
    const { value } = event.target;
    if (!isNaN(value)) {
      setCode(value);
    }
  };

  useEffect(() => {
    console.log(message);
    if (message && message.status === 200) {
      toast.success("Đổi mật khẩu thành công", {
        position: "top-right",
        onClose: () => {
          navigate("/login");
        },
      });
    }
    if (error) {
      toast.error(error, {
        position: "top-right",
      });
      dispatch(clearErrors());
    }
  }, [dispatch, error, message, navigate]);

  const submitHandler = (e) => {
    e.preventDefault();
    if (code === "") {
      toast.error("Vui lòng nhập mã xác minh", {
        position: "top-right",
      });
      return;
    }
    dispatch(verifyCode(userId, password, confirmPassword, code));
  };
  return (
    <>
      {loading ? (
        <Loader />
      ) : (
        <>
          <MetaData title={"Xác minh"} />
          <Grid className="mt-5">
            <form onSubmit={submitHandler}>
              <Paper elevation={15} className="paper">
                <Grid align="center">
                  <Avatar className="avatar">
                    <AccountCircle />
                  </Avatar>
                  <p className="text_login">NHẬP MÃ XÁC MINH</p>
                  <p className="typographyColor">
                    Mã xác minh đã được gửi đến địa chỉ email: {email}
                  </p>
                </Grid>
                <TextField
                  type="text"
                  label="Mã xác minh"
                  placeholder="Nhập mã xác minh"
                  fullWidth
                  value={code}
                  className="mb-3"
                  onChange={handleCodeChange}
                />
                <Button
                  type="submit"
                  id="login_btn"
                  onClick={submitHandler}
                  fullWidth
                  className="mb-3"
                >
                  Xác minh
                </Button>
                <Typography className="typographyColor mt-2">
                  {" "}
                  Quay trở lại trang? <Link to="/login">Đăng nhập</Link>
                </Typography>
              </Paper>
            </form>
          </Grid>
          <ToastContainer autoClose={2000} />
        </>
      )}
    </>
  );
};

export default VerifyCode;
