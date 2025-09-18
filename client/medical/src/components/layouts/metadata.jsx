import { Helmet } from "react-helmet";

const MetaData = ({ title }) => {
  return (
    <Helmet>
      <title>{`${title} - MedBooking`}</title>
    </Helmet>
  );
};

export default MetaData;
