CREATE TABLE position (
  id        BIGSERIAL        NOT NULL PRIMARY KEY,
  device_id VARCHAR          NOT NULL,
  latitude  DOUBLE PRECISION NOT NULL,
  longitude DOUBLE PRECISION NOT NULL,
  date      TIMESTAMP        NOT NULL DEFAULT current_timestamp
);

CREATE OR REPLACE FUNCTION distance(lat1 FLOAT, lon1 FLOAT, lat2 FLOAT, lon2 FLOAT)
  RETURNS FLOAT AS $$
DECLARE
  x FLOAT = 111.12 * (lat2 - lat1);
  y FLOAT = 111.12 * (lon2 - lon1) * cos(lat1 / 92.215);
BEGIN
  RETURN sqrt(x * x + y * y);
END
$$ LANGUAGE plpgsql;
