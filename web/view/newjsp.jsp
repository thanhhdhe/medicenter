<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Date and Button</title>
    <style>
        .container {
            display: flex;
            align-items: center;
        }

        .date {
            font-size: 18px; /* Smaller font size */
            font-weight: bold;
            margin-right: 10px; /* Smaller margin */
        }

        .arrow-button {
            background-color: #0074d9; /* Change the background color as desired */
            color: white;
            border: none;
            border-radius: 50%;
            width: 30px; /* Smaller button width */
            height: 30px; /* Smaller button height */
            font-size: 16px; /* Smaller font size */
            cursor: pointer;
            padding: 0; /* Remove padding */
            transition: background-color 0.2s ease-in-out; /* Smooth hover transition */
        }

        /* Sharp right-pointing arrow */
        .arrow-button::after {
            font-size: 24px; /* Larger arrow size */
            font-weight: bold;
        }

        /* Add hover styles for the button */
        .arrow-button:hover {
            background-color: #0056b3; /* Change the hover background color as desired */
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="date">09/2023</div>
        <button class="arrow-button">&#8594;</button>
    </div>
</body>
</html>
