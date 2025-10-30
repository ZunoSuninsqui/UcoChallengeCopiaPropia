import React from "react";
import googleLogo from "../assets/google.svg";
import githubLogo from "../assets/github.svg";

export default function OAuthButtons() {
  const handleClick = (provider) => alert(`${provider} login simulado`);

  const buttonStyle = {
    width: "48%",
    backgroundColor: "#5eb5fcff",
    color: "#100808ff",
    border: "1px solid #000000ff",
    borderRadius: "10px",
    padding: "10px",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
    gap: "8px",
    cursor: "pointer",
    fontSize: "0.95rem",
    transition: "all 0.2s ease",
  };

  const imgStyle = { width: "20px", height: "20px" };

  return (
    <div style={{ display: "flex", justifyContent: "space-between", marginTop: "1rem" }}>
      <button
        style={buttonStyle}
        onClick={() => handleClick("Google")}
        onMouseOver={(e) => (e.currentTarget.style.borderColor = "#fefefeff")}
        onMouseOut={(e) => (e.currentTarget.style.borderColor = "#050505ff")}
      >
        <img src={googleLogo} alt="Google" style={imgStyle} />
        Google
      </button>

      <button
        style={buttonStyle}
        onClick={() => handleClick("GitHub")}
        onMouseOver={(e) => (e.currentTarget.style.borderColor = "#fefefeff")}
        onMouseOut={(e) => (e.currentTarget.style.borderColor = "#050505ff")}
      >
        <img src={githubLogo} alt="GitHub" style={imgStyle} />
        GitHub
      </button>
    </div>
  );
}
