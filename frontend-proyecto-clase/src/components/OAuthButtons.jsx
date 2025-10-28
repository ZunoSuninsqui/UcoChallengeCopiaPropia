import React from "react";
import googleLogo from "../assets/google.svg";
import githubLogo from "../assets/github.svg";

export default function OAuthButtons({ onGoogle, onGithub, disabled }) {
  const handleClick = (provider) => {
    if (disabled) return;
    if (provider === 'google' && typeof onGoogle === 'function') return onGoogle();
    if (provider === 'github' && typeof onGithub === 'function') return onGithub();
    // fallback: no-op
    return null;
  };

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
        onClick={() => handleClick("google")}
        disabled={disabled}
        onMouseOver={(e) => (e.currentTarget.style.borderColor = "#fefefeff")}
        onMouseOut={(e) => (e.currentTarget.style.borderColor = "#050505ff")}
      >
        <img src={googleLogo} alt="Google" style={imgStyle} />
        Google
      </button>

      <button
        style={buttonStyle}
        onClick={() => handleClick("github")}
        disabled={disabled}
        onMouseOver={(e) => (e.currentTarget.style.borderColor = "#fefefeff")}
        onMouseOut={(e) => (e.currentTarget.style.borderColor = "#050505ff")}
      >
        <img src={githubLogo} alt="GitHub" style={imgStyle} />
        GitHub
      </button>
    </div>
  );
}
