import React, { Fragment, useState, useEffect } from "react";
import {
  Grid,
  Paper,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
} from "@mui/material";

const Step3 = ({ handleData }) => {
  const [book, setBook] = useState({
    category: "",
    time: "",
  });
  useEffect(() => {
    handleData(book);
  }, [book]);
  const { category, time } = book;
  const onChange = (e) => {
    setBook({ ...book, [e.target.name]: e.target.value });
  };
  return (
    <Fragment>
      <Grid className="mt-5">
        <Paper elevation={15} className="paper">
          <FormControl fullWidth className="mb-3" required>
            <InputLabel>Loại hình khám</InputLabel>
            <Select
              label="Loại khám"
              name="category"
              value={category}
              onChange={onChange}
            >
              <MenuItem value={"true"}>Dịch vụ</MenuItem>
              <MenuItem value={"false"}>BHYT</MenuItem>
            </Select>
          </FormControl>
          <FormControl fullWidth className="mb-3" required>
            <InputLabel>Buổi khám</InputLabel>
            <Select
              label="Buổi khám"
              name="time"
              value={time}
              onChange={onChange}
            >
              <MenuItem value={"true"}>Buổi sáng</MenuItem>
              <MenuItem value={"false"}>Buổi chiều</MenuItem>
            </Select>
          </FormControl>
        </Paper>
      </Grid>
    </Fragment>
  );
};

export default Step3;
