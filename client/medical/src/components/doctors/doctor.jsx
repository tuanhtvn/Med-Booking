import { Link } from "react-router-dom";

const Doctor = ({ doctor, col }) => {
  return (
    <div className={`col-sm-12 col-md-6 col-lg-${col} my-3`}>
      <div className="card p-3 rounded">
        <img
          className="card-img-top mx-auto"
          src={
            doctor.gender === "FEMALE"
              ? "/images/doctor_avatar_female.png"
              : "/images/doctor_avatar_male.png"
          }
        />

        <div className="card-body d-flex flex-column">
          <h5 className="card-title">
            <Link to={`/book/${doctor.id}`}>
              BS {doctor.title}. {doctor.fullname}
            </Link>
          </h5>
          <div className="ratings mt-auto">
            <div className="rating-outer">
              <div className="rating-inner"></div>
            </div>
          </div>
          <table>
            <tbody>
              <tr>
                <td>
                  <i className="fa fa-venus-mars mr-1"></i>Giới tính:
                </td>
                <td>{GetGender(doctor.gender)}</td>
              </tr>
              <tr>
                <td>
                  <i className="fa fa-stethoscope mr-1"></i>Chuyên khoa:
                </td>
                <td>{doctor.specialist}</td>
              </tr>
              <tr>
                <td>
                  <i className="fa fa-calendar mr-2"></i>Lịch khám:
                </td>
                <td>{doctor.schedule}</td>
              </tr>
              <tr>
                <td>
                  <i className="fa fa-dollar-sign mr-2"></i> Giá khám:
                </td>
                <td>{doctor.price}.000đ</td>
              </tr>
            </tbody>
          </table>
          <div className="ratings mt-auto">
            <div className="rating-outer">
              <div className="rating-inner"></div>
            </div>
          </div>
          <Link
            to={`/book/${doctor.id}`}
            id="select_btn"
            className="btn btn-block"
          >
            Đặt Khám
          </Link>
        </div>
      </div>
    </div>
  );
};

const GetGender = (gender) => {
  switch (gender) {
    case "MALE":
      return "Nam";
    case "FEMALE":
      return "Nữ";
    default:
      return "Không xác định";
  }
};

export default Doctor;
