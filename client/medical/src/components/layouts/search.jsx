import { useState } from "react";

const Search = ({ navigate, isRecord }) => {
  const [keyword, setKeyword] = useState("");
  let str = "Nhập tên bác sĩ ...";

  const searchHandler = (e) => {
    e.preventDefault();
    if (!isRecord) {
      if (keyword.trim()) {
        navigate(`/search/${keyword}`);
      } else {
        navigate("/");
      }
    } else {
      if (keyword.trim()) {
        navigate(`/me/records/${keyword}`);
      } else {
        navigate("/me/records");
      }
    }
  };

  return (
    <form onSubmit={searchHandler}>
      <div className="input-group">
        <input
          type="text"
          id="search_field"
          className="form-control"
          placeholder={isRecord ? "Nhập tên bệnh nhân ..." : str}
          onChange={(e) => setKeyword(e.target.value)}
        />
        <div className="input-group-append">
          <button id="search_btn" className="btn">
            <i className="fa fa-search search" aria-hidden="true"></i>
          </button>
        </div>
      </div>
    </form>
  );
};

export default Search;
