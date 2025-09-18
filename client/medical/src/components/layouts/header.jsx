import { useNavigate, Link } from "react-router-dom";
import Search from "./search";
import { useSelector, useDispatch } from "react-redux";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import "../../app.css";
import { logout, clearErrors } from "../../actions/userAction";
import { useEffect } from "react";
const Header = ({ isHome, isRecord = false }) => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const user = JSON.parse(localStorage.getItem("user"));
  const { isLogout, error } = useSelector((state) => state.auth);

  useEffect(() => {
    if (isLogout) {
      navigate("/login");
      window.location.reload();
      return;
    }
    if (error) {
      toast.error(error, {
        position: "top-right",
        onClose: () => dispatch(clearErrors()),
      });
    }
  }, [dispatch, isLogout, error]);

  const logoutHandler = () => {
    if (window.confirm("Bạn có chắc chắn muốn đăng xuất?") === true) {
      dispatch(logout());
    }
  };
  return (
    <>
      <nav className="navbar row">
        <div className="col-12 col-md-3">
          <div className="navbar-brand">
            <Link to="/">
              <img src="/images/logo.png" />
            </Link>
          </div>
        </div>

        <div className="col-12 col-md-6 mt-2 mt-md-0">
          {isHome && !isRecord && (
            <Search navigate={navigate} isRecord={false} />
          )}
          {!isHome && isRecord && (
            <Search navigate={navigate} isRecord={true} />
          )}
        </div>

        <div className="col-12 col-md-3 mt-4 mt-md-0 text-center">
          {(isHome || isRecord) && (
            <div className="ml-4 dropdown d-inline">
              <Link
                to="#!"
                className="btn dropdown-toggle text-white mr-4"
                type="button"
                id="dropDownMenuButton"
                data-toggle="dropdown"
                aria-haspopup="true"
                aria-expanded="false"
              >
                <figure className="avatar avatar-nav">
                  <img
                    src="/images/default_avatar.jpg"
                    alt={user && user.fullname + "❤️"}
                    className="rounded-circle"
                  />
                </figure>
                <span>{user && user.fullname + "❤️"}</span>
              </Link>

              <div
                className="dropdown-menu mt-3"
                aria-labelledby="dropDownMenuButton"
              >
                <Link className="dropdown-item" to="/update-password">
                  Đổi mật khẩu
                </Link>
                <Link className="dropdown-item" to="/me/records">
                  Hồ sơ
                </Link>
                <Link className="dropdown-item" to="/me/tickets">
                  Phiếu khám
                </Link>
                <Link
                  className="dropdown-item text-danger"
                  onClick={logoutHandler}
                >
                  Đăng xuất
                </Link>
              </div>
            </div>
          )}
        </div>
      </nav>
      <ToastContainer autoClose={2000} />
    </>
  );
};

export default Header;
